package dependencies

object Deps {
    private const val path = "../commonFiles/gradleScript/"
    const val dependency = "./gradleScript/dependencies.gradle"


    object Module {
        val data = ":data"
        val remote = ":remote"
        val domain = ":domain"
        val cache = ":cache"
        val stripe = ":stripe"
        val simpleCrop = ":simplecrop"
    }

    object MlKit {
        val mlVision = "com.google.firebase:firebase-ml-vision:${Version.mlKitVision}"
    }

    object Paging {
        val paging = "androidx.paging:paging-runtime:${Version.paging_version}"
    }

    object Dagger {
        val dagger2 = "com.google.dagger:dagger:${Version.dagger2}"
        val daggerAndroid = "com.google.dagger:dagger-android:${Version.dagger2}"
        val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Version.dagger2}"
        val processor = "com.google.dagger:dagger-android-processor:${Version.dagger2}"
        val compiler = "com.google.dagger:dagger-compiler:${Version.dagger2}"
    }

    object Retrofit2 {
        val adapterRxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${Version.retrofit}"
        val converterGson = "com.squareup.retrofit2:converter-gson:${Version.retrofit}"
        val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
    }

    object OkHttp3 {
        val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Version.retrofit_log}"
        val okHttp3 = "com.squareup.okhttp3:okhttp:3.12.1"
    }

    object RxJava {
        val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Version.rxAndroid}"
        val rxjava2 = "io.reactivex.rxjava2:rxjava:${Version.rx}"
        val rxrelay2 = "com.jakewharton.rxrelay2:rxrelay:${Version.rxRelay}"
        val rxBinding = "com.jakewharton.rxbinding2:rxbinding:${Version.rxBinding}"
    }

    object Gson {
        val gson = "com.google.code.gson:gson:${Version.gson}"
    }

    object Lifecycle {
        val extension = "android.arch.lifecycle:extensions:${Version.lifecycle}"
        val annotation_compliler = "android.arch.lifecycle:compiler:${Version.lifecycle}"
        val testing_core_testing = "android.arch.core:core-testing:${Version.lifecycle}"

        // ViewModel and LiveData
        val lifeCycleExtension =
                "androidx.lifecycle:lifecycle-extensions:${Version.lifecycleVersion}"
    }

    object Room {
        val room_run_time = "androidx.room:room-runtime:${Version.room_version}"
        val room_compiler = "androidx.room:room-compiler:${Version.room_version}"
        val room_rxjava = "androidx.room:room-rxjava2:${Version.room_version}"
    }


    object Firebase {
        val firebase_message = "com.google.firebase:firebase-messaging:${Version.firebase_message}"
        val firebase_invite = "com.google.firebase:firebase-invites:17.0.0"
        val firebase_analytics = "com.google.firebase:firebase-analytics:17.4.0"
        val firebase_crashlytics = "com.google.firebase:firebase-crashlytics:17.0.0"
    }

    object Location {
        val gms_maps = "com.google.android.gms:play-services-maps:16.1.0"
        val gms_location = "com.google.android.gms:play-services-location:16.0.0"
        val gms_places = "com.google.android.gms:play-services-places:16.0.0"
        val gms_auth = "com.google.android.gms:play-services-auth:17.0.0"
    }

    object Places {
        val google_places = "com.google.android.libraries.places:places:1.1.0"
    }

    object libPhoneNumber {
        val lib_phone_number = "com.googlecode.libphonenumber:libphonenumber:8.10.1"
    }

    object faceBook {
        val face_book = "com.facebook.android:facebook-android-sdk:4.31.0"
    }

    object cardView {
        val card_view = "androidx.cardview:cardview:1.0.0"
    }

    object mqtt {
        val paho_mqtt = "org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.1.1"
        val paho_mqtt_service = "org.eclipse.paho:org.eclipse.paho.android.service:1.1.1"
    }

    object mulitiDex {
        val multi_dex = "androidx.multidex:multidex:2.0.1"
    }

    object appcompat {
        val appcompat = "androidx.appcompat:appcompat:1.1.0"
    }

    object material {
        val material = "com.google.android.material:material:1.2.0-alpha02"
    }

    object fragment {
        val fragment = "androidx.fragment:fragment:1.2.0-rc03"
    }

    object constraintLayout {
        val constraint_layout = "androidx.constraintlayout:constraintlayout:1.1.3"
    }

    object io_card {
        val io_card = "io.card:android-sdk:5.5.1"
    }

    object jsoup {
        val jsoup = "org.jsoup:jsoup:1.8.3"
    }

    object lotte {
        val lotte = "com.airbnb.android:lottie:2.5.0"
    }

    object countryCode {
        val country_code = "com.hbb20:ccp:2.3.2"
    }

    val javax = "javax.inject:javax.inject:${Version.javaxInject}"
    val javaxjsr250 = "javax.annotation:jsr250-api:${Version.javaxAnnotation}"
    val security = "androidx.security:security-crypto:${Version.security}"
    val legacy_support = "androidx.legacy:legacy-support-v4:1.0.0"
    val gilde = "com.github.bumptech.glide:glide:4.5.0"
    val javax_annotation = "javax.annotation:jsr250-api:1.0"

}