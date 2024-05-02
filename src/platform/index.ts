import { NativeModules, Platform } from 'react-native';
import { createError } from './error';

const LINKING_ERROR =
  `The package 'react-native-device-auth' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

  const DeviceAuth = NativeModules.DeviceAuth
  ? NativeModules.DeviceAuth
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

function authenticate(opts:{reason:string,description?:string}):Promise<any>{
    if(Platform.OS == "android") {
      return DeviceAuth.authenticate(opts.reason,opts.description||" ")
            .catch((error:string)=>Promise.reject(createError(error))) 
    }
    return DeviceAuth.authenticate(opts.reason)
            .catch((error:string)=>Promise.reject(createError(error)))
}

function hasTouchID():Promise<any> {
    return DeviceAuth.hasTouchID()
            .catch((error:string)=>Promise.reject(createError(error)))
}

export default {authenticate,hasTouchID}