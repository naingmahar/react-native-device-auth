import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import RNDevAuth from 'react-native-device-auth';

export default function App() {
  const [result] = React.useState<number | undefined>();

  React.useEffect(() => {
    RNDevAuth.hasTouchID()
      .then((success)=>console.log("Success ",success))
      .catch((error)=>console.log("Error ",error))
  }, []);

  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
