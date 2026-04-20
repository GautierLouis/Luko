import SwiftUI
import Firebase
import App

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication,
                     didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
        print("didRegisterForRemoteNotificationsWithDeviceToken")
        Messaging.messaging().apnsToken = deviceToken  // manually forward since swizzling is off
        FirebaseAPNS().onAPNSReceived(token: deviceToken)
    }
}

@main
struct iOSApp: App {

    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    var body: some Scene {
        
        WindowGroup {
            ContentView()
        }
    }
}
