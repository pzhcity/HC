package com.nuclear.sdk.android.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.nuclear.sdk.android.api.HttpParameters;

public class Utility {
    private static char[] encodes = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    private static byte[] decodes = new byte[256];  
	public static Bundle parseUrl(String url) {
		try {
			URL u = new URL(url);
			Bundle b = decodeUrl(u.getQuery());
			b.putAll(decodeUrl(u.getRef()));
			return b;
		} catch (MalformedURLException e) {	
			return new Bundle();
		}
	}

	public static Bundle decodeUrl(String s) {
		Bundle params = new Bundle();
		if (s != null) {
			String array[] = s.split("&");
			for (String parameter : array) {
				String v[] = parameter.split("=");
				params.putString(URLDecoder.decode(v[0]), URLDecoder.decode(v[1]));
			}
		}
		return params;
	}

	@SuppressWarnings("deprecation")
	public static String encodeUrl(HttpParameters parameters) {
		if (parameters == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (int loc = 0; loc < parameters.size(); loc++) {
			if (first){
			    first = false;
			}
			else{
			    sb.append("&");
			}
			String _key=parameters.getKey(loc);
			String _value=parameters.getValue(_key);
			if(_value==null){
			    Log.i("encodeUrl", "key:"+_key+" 's value is null");
			}
			else{
			    sb.append(URLEncoder.encode(parameters.getKey(loc)) + "="
                        + URLEncoder.encode(parameters.getValue(loc)));
			}
			
		}
		return sb.toString();
	}

	
	
	public static String encodeParameters(HttpParameters httpParams) {
		if (null == httpParams || isBundleEmpty(httpParams)) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		int j = 0;
		for (int loc = 0; loc < httpParams.size(); loc++) {
			String key = httpParams.getKey(loc);
			if (j != 0) {
				buf.append("&");
			}
			try {
				buf.append(URLEncoder.encode(key, "UTF-8")).append("=")
						.append(URLEncoder.encode(httpParams.getValue(key), "UTF-8"));
			} catch (java.io.UnsupportedEncodingException neverHappen) {
			}
			j++;
		}
		return buf.toString();

	}

	public static void showAlert(Context context, String title, String text) {
		Builder alertBuilder = new Builder(context);
		alertBuilder.setTitle(title);
		alertBuilder.setMessage(text);
		alertBuilder.create().show();
	}

	private static boolean isBundleEmpty(HttpParameters bundle) {
		if (bundle == null || bundle.size() == 0) {
			return true;
		}
		return false;
	}
	/**
	 * ��data�����Base62���ַ�
	 * @param data 
	 * @return
	 */
	public static String encodeBase62(byte[] data) {  
	    StringBuffer sb = new StringBuffer(data.length * 2);  
	    int pos = 0, val = 0;  
	    for (int i = 0; i < data.length; i++) {  
	        val = (val << 8) | (data[i] & 0xFF);  
	        pos += 8;  
	        while (pos > 5) {  
	            char c = encodes[val >> (pos -= 6)];  
	            sb.append(  
	            /**/c == 'i' ? "ia" :  
	            /**/c == '+' ? "ib" :  
	            /**/c == '/' ? "ic" : c);  
	            val &= ((1 << pos) - 1);  
	        }  
	    }  
	    if (pos > 0) {  
	        char c = encodes[val << (6 - pos)];  
	        sb.append(  
	        /**/c == 'i' ? "ia" :  
	        /**/c == '+' ? "ib" :  
	        /**/c == '/' ? "ic" : c);  
	    }  
	    return sb.toString();  
	}  
	  /**
	   * ���ַ�����byte����
	   * @param data
	   * @return
	   */
	public static byte[] decodeBase62(String string) {  
	    if(string==null){
	        return null;
	    }
	    char[] data=string.toCharArray();
	    ByteArrayOutputStream baos = new ByteArrayOutputStream(string.toCharArray().length);  
	    int pos = 0, val = 0;  
	    for (int i = 0; i < data.length; i++) {  
	        char c = data[i];  
	        if (c == 'i') {  
	            c = data[++i];  
	            c =  
	            /**/c == 'a' ? 'i' :  
	            /**/c == 'b' ? '+' :  
	            /**/c == 'c' ? '/' : data[--i];  
	        }  
	        val = (val << 6) | decodes[c];  
	        pos += 6;  
	        while (pos > 7) {  
	            baos.write(val >> (pos -= 8));  
	            val &= ((1 << pos) - 1);  
	        }  
	    }  
	    return baos.toByteArray();  
	}  
	private static boolean deleteDependon(File file, int maxRetryCount)
	  {
	    int retryCount = 1;
	    maxRetryCount = (maxRetryCount < 1) ? 5 : maxRetryCount;
	    boolean isDeleted = false;

	    if (file != null) {
	      while ((!(isDeleted)) && (retryCount <= maxRetryCount) && (file.isFile()) && (file.exists()))
	        if (!((isDeleted = file.delete()))) {
//	          LogUtils.i(file.getAbsolutePath() + "ɾ��ʧ�ܣ�ʧ�ܴ���Ϊ:" + retryCount);
	          ++retryCount;
	        }


	    }

	    return isDeleted;
	  }
	
	
	private static void mkdirs(File dir_)
	  {
		  if(dir_==null){
			   return;
		  }
	    if ((!(dir_.exists())) && (!(dir_.mkdirs())) ) throw new RuntimeException("fail to make " + dir_.getAbsolutePath());
	  }
	 private static void createNewFile(File file_)
	  {
		  if(file_==null){
			   return;
		  }
	    if (!(__createNewFile(file_))) throw new RuntimeException(file_.getAbsolutePath() + " doesn't be created!");
	  }
	  private static void delete(File f)
	  {
	    if ((f != null) && (f.exists()) && (!(f.delete())) ) {
	    	throw new RuntimeException(f.getAbsolutePath() + " doesn't be deleted!");
	    }
	    	
	  }
	  private static boolean __createNewFile(File file_)
	  {
		  if(file_==null){
			   return false;
		  }
	    makesureParentExist(file_);
	    if (file_.exists())
	      delete(file_);
	    try
	    {
	      return file_.createNewFile();
	    }
	    catch (IOException e) {
	     e.printStackTrace();
	    }

	    return false;
	  }


	  private static boolean deleteDependon(String filepath, int maxRetryCount)
	  {
	    if (TextUtils.isEmpty(filepath)) return false;
	    return deleteDependon(new File(filepath), maxRetryCount);
	  }

	private static boolean deleteDependon(String filepath)
	  {
	    return deleteDependon(filepath, 0);
	  }


	  private static boolean doesExisted(File file)
	  {
	    return ((file != null) && (file.exists()));
	  }

	  private static boolean doesExisted(String filepath)
	  {
	    if (TextUtils.isEmpty(filepath)) return false;
	    return doesExisted(new File(filepath));
	  }
	  private static void makesureParentExist(File file_)
	  {
	   if(file_==null){
		   return;
	  }
	    File parent = file_.getParentFile();
	    if ((parent != null) && (!(parent.exists())))
	      mkdirs(parent);
	  }

//	  private static void makesureParentExist(String filepath)
//	  {
//	    if(filepath==null){
//	    	return;
//	    }
//	    makesureParentExist(new File(filepath));
//	  }
	  private static void makesureFileExist(File file)
	  {
	   if(file==null)
		   return;
	    if (!(file.exists())) {
	      makesureParentExist(file);
	      createNewFile(file);
	    }
	  }

	  private static void makesureFileExist(String filePath_)
	  {
		  if(filePath_==null)
			   return;
	    makesureFileExist(new File(filePath_));
	  }
	  //�жϵ�ǰ�����Ƿ�Ϊwifi
	    public static boolean isWifi(Context mContext) {  
	 	   ConnectivityManager connectivityManager = 
	 		   (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);  
	 	   NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();  
	 	   if (activeNetInfo != null  && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI){  
	     	        return true;  
	     	 }  
	     return false;  
	    }

	
        private static Bitmap safeDecodeBimtapFile( String bmpFile, BitmapFactory.Options opts ) {
            BitmapFactory.Options optsTmp = opts;
            if ( optsTmp == null ) {
                optsTmp = new BitmapFactory.Options();
                optsTmp.inSampleSize = 1;
            }
            
            Bitmap bmp = null;
            FileInputStream input = null;
            
            final int MAX_TRIAL = 5;
            for( int i = 0; i < MAX_TRIAL; ++i ) {
                try {
                    input = new FileInputStream( bmpFile );
                    bmp = BitmapFactory.decodeStream(input, null, opts);
                    try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
                    break;
                }
                catch( OutOfMemoryError e ) {
                    e.printStackTrace();
                    optsTmp.inSampleSize *= 2;
                    try {
						input.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
                }
                catch (FileNotFoundException e) {
                    break;
                }
            }
            
            return bmp;
        }
    }
	
