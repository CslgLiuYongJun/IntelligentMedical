# IntelligentMedical
## 首先在build.grade的dependencise加入以下语句
implementation 'com.android.support:design:27.1.0'  
testImplementation 'junit:junit:4.12'  
androidTestImplementation 'com.android.support.test: runner :1.0.2'  
androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'  
implementation 'com.github.arimorty:floatingsearchview:2.1.1'  
implementation project(':libzxing')  
implementation files('libs/okio-1.13.0.jar')  
implementation files('libs/okhttp-2.7.5.jar')  
## 设计布局文件
### 引入其他库
比如FloatingSearchView在xml中加入
<com.arlib.floatingsearchview.FloatingSearchView></com.arlib.floatingsearchview.FloatingSearchView>
## 程序入口
MainActivity
## 子界面
doctor
## 获取信息类
info_doctor、info_nurse、info_patient、info_record、list_info_doctor、section_doctor
## 获取后台信息的方法
OkHttpClient进行网络请求解析json数据
