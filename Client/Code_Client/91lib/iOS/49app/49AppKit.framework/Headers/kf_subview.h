//
//  kf_subview.h
//  49AppKit
//
//  Created by 14zynr on 13-7-26.
//  Copyright (c) 2013年 __jc_49app__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "zmd_uilabel.h"

@protocol _kf_delegate;
@interface kf_subview : UIView < UITextViewDelegate >
{
    /* transform */
    CGAffineTransform textloadview_transform;
    
    UIView *_mobile_alertview;
}

@property (nonatomic, assign) id <_kf_delegate> __kf_delegate;
@property (nonatomic, retain) NSDictionary *_main_info_dic;
@property (nonatomic, retain) NSString *_mobile;

- (id)initWithFrame:(CGRect)frame withInfo:(NSDictionary *)_info_dic;
- (void)_load_data;

@end

@protocol _kf_delegate <NSObject>
    - (void)_foundpassformobile_action:(id)_btn;
@end
