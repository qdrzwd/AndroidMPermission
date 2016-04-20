
android m权限获取
==================================

android m权限获取，利用aspectj实现一行注解获取权限


使用
-----

主工程目录的build.gradle文件中添加插件`buildscript`'s `dependencies` :
```groovy
classpath 'com.uphyca.gradle:gradle-android-aspectj-plugin:0.9.14'
```

app工程build.gradle文件添加 `android-aspectj` 插件:
```groovy
apply plugin: 'com.uphyca.android-aspectj'
```

License
-------

    Copyright 2014 qdrzwd, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.å
