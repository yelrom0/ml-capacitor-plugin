import Foundation

@objc public class CapML: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
