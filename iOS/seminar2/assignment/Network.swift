import Foundation

struct ResponseData: Codable {
    var statusCode: Int
    var body: [Member]
}

struct Member: Codable {
    var id: Int
    var name: String
    var team: String
}

let baseUrl = "https://jkhi75xm0a.execute-api.ap-northeast-2.amazonaws.com/waffle/"
let memberUrl = baseUrl + "/members/"

struct WaffleNetwork {
    static func request(with urlString: String, done: @escaping ([Member]) -> (), failure: @escaping () -> ()) {
        if let url = URL(string: urlString) {
            let session = URLSession(configuration: .default)
            let task = session.dataTask(with: url) { (data, response, error) in
                if let response = response as? HTTPURLResponse {
                    if response.statusCode == 403 {
                        failure()
                        return
                    }
                }
                
                if let error = error {
                    print(error)
                    failure()
                    return
                }
                
                guard let data = data else {
                    print("야야야")
                    failure()
                    return
                }
                
                guard let memberList = parseJSON(data) else {
                    print("야야야야")
                    failure()
                    return
                }
                
                done(memberList)
            }
            
            task.resume()
        }
    }
    
    static func parseJSON(_ timetableData: Data) -> [Member]? {
        let decoder = JSONDecoder()
        do {
            let decodedData = try decoder.decode(ResponseData.self, from: timetableData)
            
            return decodedData.body
            
        } catch let error {
            print("error", error)
            return nil
        }
    }
}

WaffleNetwork.request(with: memberUrl) { (memberList) in
    print(memberList)
} failure: {
    print("error")
}
