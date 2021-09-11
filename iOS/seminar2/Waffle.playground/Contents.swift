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

enum NetworkError {
    case serverError
    case authError
    case parsingError
}

let timetableDataUrl = "https://waffle-ios.s3.ap-northeast-2.amazonaws.com/waffle-ioss.json"

struct WaffleNetwork {
    static func request(with urlString: String, done: @escaping (WaffleTimetableList) -> (), failure: @escaping (NetworkError) -> ()) {
        if let url = URL(string: urlString) {
            let session = URLSession(configuration: .default)
            let task = session.dataTask(with: url) { (data, response, error) in
                if let response = response as? HTTPURLResponse {
                    if response.statusCode == 403 {
                        failure(.authError)
                        return
                    }
                }
                
                if let error = error {
                    print(error)
                    failure(.serverError)
                    
                    return
                }
                
                guard let data = data else {
                    failure(.serverError)
                    return
                }
                
                guard let timetableList = parseJSON(data) else {
                    failure(.parsingError)
                    return
                }
                
                done(timetableList)
            }
            
            task.resume()
        }
    }
    
    static func parseJSON(_ timetableData: Data) -> WaffleTimetableList? {
        let decoder = JSONDecoder()
        do {
            let decodedData = try decoder.decode([WaffleTimetable].self, from: timetableData)
            
            let timetableList = WaffleTimetableList(decodedData)
            
            print(timetableList.timetableList[0].course_title)
            return timetableList
            
        } catch let error {
            print("error", error)
            return nil
        }
    }
}

WaffleNetwork.request(with: timetableDataUrl) { (timetableList) in
    print(timetableList)
} failure: { (error) in
    switch error {
    case .serverError:
        print("서버에 문제가 생겼습니다")
    case .parsingError:
        print("데이터를 처리하는데 문제가 발생했습니다")
    case .authError:
        print("권한이 없습니다")
    }
}
