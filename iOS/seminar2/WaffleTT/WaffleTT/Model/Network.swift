//
//  Network.swift
//  WaffleTT
//
//  Created by Jinsup Keum on 2021/09/12.
//

import Foundation

enum NetworkError {
    case serverError
    case authError
    case parsingError
    
    func getMessage() -> String {
        switch self {
        case .serverError: return "서버에 문제가 생겼습니다"
        case .authError: return "권한이 없습니다"
        case .parsingError: return "데이터를 처리하는데 문제가 생겼습니다."
        }
    }
}

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
