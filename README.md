# react-native-device-auth

React Native KeyguardManager And LocalAuthentication Lib (Android and Ios)

## Installation

```sh
npm install @nmh/react-native-device-auth
```

#### Android

1. Insert the following lines `android/app/src/AndroidManifest.xml`:
    ```
    <uses-permission android:name="android.permission.USE_FINGERPRINT" />
    <uses-permission android:name="android.permission.USE_BIOMETRIC" />
    ```

#### IOS

1. Insert the following lines `ios/project/info.plist`:
    ```
    <key>NSFaceIDUsageDescription</key>
    <string>Enabling Face ID allows you quick and secure access to your account.</string>
    ```



## Usage

```js
import RNDeviceAuth from '@nmh/react-native-device-auth';;

// ...

const LABEL = 'This is a secure area, please authenticate yourself';

authenticate () {
        return new Promise((resolve,reject)=>{
            RNDeviceAuth.authenticate({reason:this._LABEL})
                .then((status:any)=>console.log(status))
                .catch((error:any)=>console.log(error))
        })
}

```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
