'use strict';

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  Alert,
  View
} from 'react-native';

import NativeToastAndroid from './ModuleToastAndroid';

const show = () => {
  //Alert.alert('Button has been Pressed!!!');
  NativeToastAndroid.show('Toast From Js',NativeToastAndroid.SHORT);
}

const resultCallBack = () => {
  NativeToastAndroid.showCallBackInfo('Toast CallBack',NativeToastAndroid.LONG,
  (info) => {
    Alert.alert('sleep time:' + info);
  }
  );
}

class HelloWorld extends React.Component {

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.hello} onPress={show}>showToast</Text>
        <Text style={styles.hello} onPress={resultCallBack}>showCallBackInfo</Text>
      </View>
    )
  }
}

var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
  },
  hello: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
});

AppRegistry.registerComponent('androidrn', () => HelloWorld);
