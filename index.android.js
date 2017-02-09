'use strict';

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  Alert,
  View,
  DeviceEventEmitter
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

  componentDidMount(){
    //事件注册 - 注意名称的对应
    DeviceEventEmitter.addListener('EventSend',(info)=>{
         Alert.alert(info);
    });
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.hello} onPress={show}>showToast</Text>
        <Text style={styles.hello} onPress={resultCallBack}>showCallBackInfo</Text>
      </View>
    );
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
