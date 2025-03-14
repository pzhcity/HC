//
//  libKuaiyongObj.h
//  libKuaiyong
//
//  Created by wzy on 13-3-5.
//  Copyright (c) 2013年 youai. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@interface libKuaiyongObj : NSObject
{
    UIActivityIndicatorView *waitView;
}
-(void) registerNotification;
-(void) SNSLoginResult:(NSNotification *)notify;
-(void) SNSInitResult:(NSNotification *)notify;
-(void) NdUniPayAysnResult:(NSNotification *)notify;
-(void) unregisterNotification;
-(void) updateApp;

@end
