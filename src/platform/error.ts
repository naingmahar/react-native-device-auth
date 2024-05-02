
const ERRORS = require('./data/errors')

interface IDeviceAuthError{name:string,message:string,details:string}

function deviceAuthError(name:string, details:any) {
  let obj:IDeviceAuthError = {
    name:name || 'DeviceAuthError',
    message:details.message || 'Device Authentication Error',
    details:details || {}
  }
  return obj
}


export function createError(error:string) {
  let details = ERRORS[error]
  details.name = error

  return deviceAuthError(error, details)
}
