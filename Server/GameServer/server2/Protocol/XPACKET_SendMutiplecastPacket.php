<?php
/**
	Auto generated by xproto.exe
@author
	Dany
*/
require_once("XByteArray.php");
require_once($GLOBALS['GAME_ROOT'] . "Protocol/XLONGLONG.php");
require_once($GLOBALS['GAME_ROOT'] . "Protocol/XLONGLONG.php");


	class XPACKET_SendMutiplecastPacket
	{
		    public static  $_m_xcmd/*:int*/=_EMSG_PushInterface::CMSG_SendMutiplecastPacket;

	    
		public $request_id/*:XLONGLONG*/;
		public $bIncluded/*:UCHAR*/;
		public $socketIdList =array();/*ULONGLONG[]*/
		public $pPacket="";/*UCHAR[]*/
		public $bKickOut/*:UCHAR*/;

	    
	    public function XPACKET_SendMutiplecastPacket()
		{
	        $this->request_id =new XLONGLONG();
        $this->bIncluded =0;/*UCHAR*/
        $this->bKickOut =0;/*UCHAR*/

		}
			
		public static function _Size($request_id/*:XLONGLONG*/ ,$bIncluded/*:UCHAR*/ ,$socketIdList/*:XLONGLONG[] */ ,$pPacket/*:UCHAR[] */ ,$bKickOut/*:UCHAR*/ )
		{
			$__xv	= 0;
			$i		=0;

            $__xv +=4;//LENGHT
            $__xv +=4;//_m_xcmd
            $__xv +=$request_id->Size();
            $__xv +=1;//bIncluded
            $__xv +=4;//XByteArray::GetDynamicLengthNumBytes(socketIdList.length);
             foreach($socketIdList as $_elem) $__xv += $_elem->Size();
            $__xv +=4;//XByteArray::GetDynamicLengthNumBytes(pPacket.length);
            $__xv +=1*strlen($pPacket);
            $__xv +=1;//bKickOut

			return $__xv;
		}
				
		public static function _ToBuffer($__dst/*XByteArray*/,$request_id/*:XLONGLONG*/ ,$bIncluded/*:UCHAR*/ ,$socketIdList/*:XLONGLONG[] */ ,$pPacket/*:UCHAR[] */ ,$bKickOut/*:UCHAR*/ )
		{
			$__xv	= 0;
			$i		=0;

			$__dst->writeInt32(XPACKET_SendMutiplecastPacket::_Size($request_id/*:XLONGLONG*/ ,$bIncluded/*:UCHAR*/ ,$socketIdList/*:XLONGLONG[] */ ,$pPacket/*:UCHAR[] */ ,$bKickOut/*:UCHAR*/ ));
			$__xv +=4;
			$__dst->writeInt32(XPACKET_SendMutiplecastPacket::$_m_xcmd);
			$__xv +=4;
            $__xv +=$request_id->ToBuffer($__dst);

            //Write codes of bIncluded
            //
            $__dst->writeByte($bIncluded);
            $__xv +=1;

            //Write codes of socketIdList
            //
            $__num = count($socketIdList,0);
            $__xv +=XByteArray::WriteDynamicArrayLength($__dst,$__num);
            for($i=0;$i<$__num;$i++)
            {
                 $__xv +=$socketIdList[$i]->ToBuffer($__dst);
            }

            //Write codes of pPacket
            //
            $__num = strlen($pPacket);
            $__xv +=XByteArray::WriteDynamicArrayLength($__dst,$__num);
            $__dst->writeBinary($pPacket,$__num);
            $__xv +=$__num;
            

            //Write codes of bKickOut
            //
            $__dst->writeByte($bKickOut);
            $__xv +=1;

			return $__xv;
		}
		
		public static function _ClassFromParameters($request_id/*:XLONGLONG*/ ,$bIncluded/*:UCHAR*/ ,$socketIdList/*:XLONGLONG[] */ ,$pPacket/*:UCHAR[] */ ,$bKickOut/*:UCHAR*/ )
		{
			$_class = new XPACKET_SendMutiplecastPacket();

            $_class->request_id=$request_id;
            $_class->bIncluded=$bIncluded;
            $_class->socketIdList=$socketIdList;
            $_class->pPacket=$pPacket;
            $_class->bKickOut=$bKickOut;
			
			return $_class;
		}


		public function FromBuffer($__src/*:XByteArray*/)
		{
			$__xv					= 0;
			$i						=0;
			$__xvBeanSize	=0;


            //Read codes of __xvtemp1
            //
            if($__src->getBytesAvailable()>=4)
            {
                $this->__xvtemp1=$__src->readInt32();
                $__xv +=4;
            }
            else
            {
                return 0;
            }

            //Read codes of __xvtemp2
            //
            if($__src->getBytesAvailable()>=4)
            {
                $this->__xvtemp2=$__src->readInt32();
                $__xv +=4;
            }
            else
            {
                return 0;
            }
            $__xv +=$this->request_id->FromBuffer($__src);

            //Read codes of bIncluded
            //
            if($__src->getBytesAvailable()>=1)
            {
                $this->bIncluded=$__src->readByte();
                $__xv +=1;
            }
            else
            {
                return 0;
            }

            //Read codes of socketIdList
            //
            $__socketIdList_arrlen = new XInteger();
            $__xv +=XByteArray::ReadDynamicArrayLength($__src,$__socketIdList_arrlen);
            if($__socketIdList_arrlen->_value<0)
            {
                return 0;
            }
            $this->socketIdList =array();
            for($i=0;$i<$__socketIdList_arrlen->_value;$i++)
            {
               $__xvtmp_socketIdList = new XLONGLONG();
               {
                   $__xvBeanSize =$__xvtmp_socketIdList->FromBuffer($__src);
                   if($__xvBeanSize<=0) return 0;
                   $__xv +=$__xvBeanSize;
                   array_push($this->socketIdList, $__xvtmp_socketIdList);
               } 
            }

            //Read codes of pPacket
            //
            $__pPacket_arrlen = new XInteger();
            $__xv +=XByteArray::ReadDynamicArrayLength($__src,$__pPacket_arrlen);
            if($__pPacket_arrlen->_value<0)
            {
                return 0;
            }
            if($__src->getBytesAvailable()>=$__pPacket_arrlen->_value)
            {
                $this->pPacket=$__src->readBinary($__pPacket_arrlen->_value);
                $__xv +=$__pPacket_arrlen->_value;
            }
            else 
            {
                return 0;
            }
            

            //Read codes of bKickOut
            //
            if($__src->getBytesAvailable()>=1)
            {
                $this->bKickOut=$__src->readByte();
                $__xv +=1;
            }
            else
            {
                return 0;
            }

			return $__xv;
		}
		
		public function ToBuffer($__dst/*XByteArray*/)
		{
			return XPACKET_SendMutiplecastPacket::_ToBuffer($__dst,$this->request_id ,$this->bIncluded ,$this->socketIdList ,$this->pPacket ,$this->bKickOut );
		}
		
		public function Size()
		{
			return XPACKET_SendMutiplecastPacket::_Size($this->request_id ,$this->bIncluded ,$this->socketIdList ,$this->pPacket ,$this->bKickOut );
		}

    public  function FromXml(/*XP_XMLNODE_PTR*/ $pNode)
    {

        $this->request_id = XFROM_XML($this->request_id,$pNode,"request_id",3,"");
        $this->bIncluded = XFROM_XML($this->bIncluded,$pNode,"bIncluded",0,"");
        $this->socketIdList = XFROM_XML($this->socketIdList,$pNode,"socketIdList",5,"XLONGLONG",3);
        $this->pPacket = XFROM_XML($this->pPacket,$pNode,"pPacket",4,"",0);
        $this->bKickOut = XFROM_XML($this->bKickOut,$pNode,"bKickOut",0,"");

		  	return 0;
    }
    
    public  function   ToXml(/*XSTRING_STREAM & out*/)
    {
        $__xv_out="";

        $__xv_out .= XTO_XML($this->request_id,"request_id",3, 0);
        $__xv_out .= XTO_XML($this->bIncluded,"bIncluded",0, 0);
        $__xv_out .= XTO_XML($this->socketIdList,"socketIdList",5, 3);
        $__xv_out .= XTO_XML($this->pPacket,"pPacket",4, 0);
        $__xv_out .= XTO_XML($this->bKickOut,"bKickOut",0, 0);

        return $__xv_out;
    }
    
    public   function fromAMFObject($pNode)
    {
       
        
        $this->request_id = XFROM_AMFOBJECT($this->request_id,$pNode,"request_id",3,"");
        $this->bIncluded = XFROM_AMFOBJECT($this->bIncluded,$pNode,"bIncluded",0,"");
        $this->socketIdList = XFROM_AMFOBJECT($this->socketIdList,$pNode,"socketIdList",5,"XLONGLONG",3);
        $this->pPacket = XFROM_AMFOBJECT($this->pPacket,$pNode,"pPacket",4,"",0);
        $this->bKickOut = XFROM_AMFOBJECT($this->bKickOut,$pNode,"bKickOut",0,"");

        return 0;
    }
		
	private static function ParamDebugString($param)
    {
    	if (is_object($param))
    	{
    		return $param->ToDebugString();
    	}
    	else if (is_array($param))
    	{
    		$str = "(";
    		foreach($param as $p)
    		{
    			if( is_object($p) )
    			{
    				$str .= $p->ToDebugString().",";
			}
			else
			{
				$str .= strval($p).",";
			}
    		}
    		$str .= ")";
    		$str = str_replace(",)",")",$str);
    		return $str;
    	}
    	
    	return strval($param);
    } 
    
	public  function ToDebugString()
    {
    	if(XPACKET_SendMutiplecastPacket::$_m_xcmd == _EMSG_ServerEvent::SMSG_OnSendZipData){
    		return "([ignore zip data])";
    	}
		
    	$__xv_out  = "(";
    	
        $__xv_out .= "request_id=".$this->ParamDebugString($this->request_id).",";
        $__xv_out .= "bIncluded=".$this->ParamDebugString($this->bIncluded).",";
        $__xv_out .= "socketIdList=".$this->ParamDebugString($this->socketIdList).",";
        $__xv_out .= "pPacket=".$this->ParamDebugString($this->pPacket).",";
        $__xv_out .= "bKickOut=".$this->ParamDebugString($this->bKickOut).",";

    	
    	$__xv_out  .= ")";
    	
    	$__xv_out = str_replace(",)",")",$__xv_out);
    	
    	return $__xv_out;
    }
    
   	public static function toAMFObject($__dst/*XByteArray*/,$request_id/*:XLONGLONG*/ ,$bIncluded/*:UCHAR*/ ,$socketIdList/*:XLONGLONG[] */ ,$pPacket/*:UCHAR[] */ ,$bKickOut/*:UCHAR*/ )
		{
			$__xv	= 0;
			$i		=0;
      $obj = array();

        $obj["request_id"]=$request_id ;
        $obj["bIncluded"]=$bIncluded ;
        $obj["socketIdList"]=$socketIdList ;
        $obj["pPacket"]=$pPacket ;
        $obj["bKickOut"]=$bKickOut ;

      if(count($obj)>0)
      {
          $outBuffer  = WriteAMF3Object($obj);
          $__xv = strlen($outBuffer);
          $__xv+=8;
          $__dst->writeInt32($__xv);
          $__dst->writeInt32(XPACKET_SendMutiplecastPacket::$_m_xcmd);
          $__dst->writeBinary($outBuffer,strlen($outBuffer));
      }
      else
      {
          $__xv =8;
          $__dst->writeInt32($__xv);
          $__dst->writeInt32(XPACKET_SendMutiplecastPacket::$_m_xcmd);
      }
      
      
			return $__xv;
		}
	}
	
	
?>
