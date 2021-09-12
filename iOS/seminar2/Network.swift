import Foundation

struct WaffleTimetable: Codable {
    var classification: String // "교양",
    var category: String // "문화와 예술",
    var instructor: String // "전예완",
    var course_number: String //  "L0546.000500",
    var _id: String // "60f174d17fa06e00b481a9ef",
    var course_title: String // "공연예술의 이해",
    var credit: Int // 3,
}

struct WaffleTimetableList {
    var timetableList: [WaffleTimetable]
    
    init(_ timetableList: [WaffleTimetable]) {
        self.timetableList = timetableList
    }
}

let timetableDataUrl = "https://waffle-ios.s3.ap-northeast-2.amazonaws.com/timetable.json"

struct WaffleNetwork {
    static func request() {}
    static func parseJSON(_ timetableData: Data) -> WaffleTimetableList? {}
}
