#import <React/RCTBridgeModule.h>
#import "RCTUtils.h"

@interface RCT_EXTERN_MODULE(DeviceAuth, NSObject)

RCT_EXTERN_METHOD(multiply:(float)a withB:(float)b
                 withResolver:(RCTPromiseResolveBlock)resolve
                  withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(authenticate:(NSString*)reason
                 withResolver:(RCTPromiseResolveBlock)resolve
                  withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(hasTouchID: (RCTResponseSenderBlock)callback)


+ (BOOL)requiresMainQueueSetup
{
  return NO;
}

@end
