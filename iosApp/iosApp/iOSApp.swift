import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    init(){
        startKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

func startKoin(){
    KoinInitKt.doInitKoin()
}
