//
//  WaffleTimetable.swift
//  WaffleTT
//
//  Created by Jinsup Keum on 2021/09/11.
//

import Foundation

struct WaffleTimetable: Codable, Equatable {
    var classification: String // "교양",
    var category: String // "문화와 예술",
    var instructor: String // "전예완",
    var course_number: String //  "L0546.000500",
    var _id: String // "60f174d17fa06e00b481a9ef",
    var course_title: String // "공연예술의 이해",
    var credit: Int // 3,
}

struct WaffleTimetableList: Codable {
    var timetableList: [WaffleTimetable]
    
    init(_ timetableList: [WaffleTimetable]) {
        self.timetableList = timetableList
    }
}

struct WaffleTimetableManager {
    static func saveToUserDefault(timetableList: [WaffleTimetable] ) {
        UserDefaults.standard.set(try? PropertyListEncoder().encode(timetableList), forKey:"WaffleTimeTableList")
    }
    
    static func getSavedTimetable() -> [WaffleTimetable] {
        if let data = UserDefaults.standard.value(forKey:"WaffleTimeTableList") as? Data {
            if let timetableList = try? PropertyListDecoder().decode([WaffleTimetable].self, from: data) {
                return timetableList
            }
            return []
        }
        return []
    }
}
