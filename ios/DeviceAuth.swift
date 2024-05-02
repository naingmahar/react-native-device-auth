import Foundation
import LocalAuthentication

@objc(DeviceAuth)
class DeviceAuth: NSObject {
    
    class DeviceAuthErrors {
        static let LOCALAUTHSUCCESS = "success"
        static let AUTHFAIL = "auth_fail"
        static let AUTHPASSCODEFAIL  = "auth_passcode_fail"
        static let NOBIOMETRY  = "no_biometry"
        static let SUPPORTAUTH  = "support_local_auth"
        static let NOAUTH  = "local_auth_not_support"
    }
    
    @objc func hasTouchID(resolve:@escaping RCTPromiseResolveBlock,reject:@escaping RCTPromiseRejectBlock) -> Void  {
        let context = LAContext()
        var error: NSError?
        
        if context.canEvaluatePolicy(.deviceOwnerAuthentication, error: &error) {
            resolve(DeviceAuthErrors.SUPPORTAUTH)
        } else {
            reject("ERROR",DeviceAuthErrors.NOAUTH,nil)
        }
    }
    
    
    @objc(multiply:withB:withResolver:withRejecter:)
    func multiply(a: Float, b: Float, resolve:RCTPromiseResolveBlock,reject:RCTPromiseRejectBlock) -> Void {
        resolve(a*b)
    }
    
    @objc(authenticate:withResolver:withRejecter:)
    func authenticate(reason: String,
                      resolve:@escaping RCTPromiseResolveBlock,reject:@escaping RCTPromiseRejectBlock) -> Void {
        let context = LAContext()
        var error: NSError?
        
        if context.canEvaluatePolicy(.deviceOwnerAuthentication, error: &error) {
            context.evaluatePolicy(.deviceOwnerAuthentication, localizedReason: reason) { success, error in
                
                DispatchQueue.main.async {
                    if success{
                        resolve(DeviceAuthErrors.LOCALAUTHSUCCESS)
                    }else{
                        reject("ERROR",DeviceAuthErrors.AUTHFAIL,nil)
                    }
                }
            }
        }else {
            reject("ERROR",DeviceAuthErrors.NOAUTH,nil)
            // no biometry
        }
    }
    
}
