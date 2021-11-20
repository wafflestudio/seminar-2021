//
//  APIPopularMovies.swift
//  seminar4
//
//  Created by 한상현 on 2021/10/17.
//

import Alamofire
import RxSwift

struct APIConstants {
    static let baseURL = "https://api.themoviedb.org/3"
    static let apiKey = "your api key"
    static let imageBaseURL = "https://image.tmdb.org/t/p/w500"
}

enum NetworkError: Error {
    case emptyData
}

class APIRequester<T: Decodable> {
    static func request(parameter: [String: Any], url: String, method: HTTPMethod) -> Single<T>{
        return Single<T>.create { single in
            let _ = AF.request(url, method: method, parameters: parameter)
            .responseData { response in
                switch response.result {
                case .success:
                    guard let data = response.data,
                          let result = try? JSONDecoder().decode(T.self, from: data) as T else {
                        return
                    }
                    single(.success(result))
                default:
                    single(.failure(NetworkError.emptyData))
                }
            }
            
            return Disposables.create()
        }
    }
}

class APIPopularMovies {
    static let apiURL = "/movie/popular"
    static let method: HTTPMethod = .get
    
    static func request(page: Int = 1) -> Observable<[Movie]> {
        let parameter: [String: Any] = [
            "api_key": APIConstants.apiKey,
            "language": "ko",
            "page" : page
        ]
        
        let requestPopularMovies = APIRequester<ResMoviesData>.request(parameter: parameter, url: APIConstants.baseURL + Self.apiURL, method: Self.method)
        
        let convertMovieArrayFromResponse: (ResMoviesData) throws -> [Movie] = { resMoviesData in
            guard let resMovieArray = resMoviesData.results else { throw NetworkError.emptyData }
                    
            return resMovieArray.map { movie in
                let formatter = NumberFormatter()
                formatter.numberStyle = .decimal
                formatter.maximumFractionDigits = 2
                let voteAverage = formatter.string(for: movie.vote_average)
                
                return Movie(title: movie.title ?? "",
                             score: voteAverage ?? "",
                             imageURL: movie.poster_path ?? "",
                             overview: movie.overview ?? "")
            }
        }
        
        return requestPopularMovies
            .asObservable()
            .map(convertMovieArrayFromResponse)
    }
}

class APITopRatedMovies {
    static let apiURL = "/movie/top_rated"
    static let method: HTTPMethod = .get
    
    static func request(page: Int = 1) -> Observable<[Movie]> {
        let parameter: [String: Any] = [
            "api_key": APIConstants.apiKey,
            "language": "ko",
            "page" : page
        ]
        
        let requestTopRatedMovies = APIRequester<ResMoviesData>.request(parameter: parameter, url: APIConstants.baseURL + Self.apiURL, method: Self.method)
        
        let convertMovieArrayFromResponse: (ResMoviesData) throws -> [Movie] = { resMoviesData in
            guard let resMovieArray = resMoviesData.results else { throw NetworkError.emptyData }
                    
            return resMovieArray.map { movie in
                let formatter = NumberFormatter()
                formatter.numberStyle = .decimal
                formatter.maximumFractionDigits = 2
                let voteAverage = formatter.string(for: movie.vote_average)
                
                return Movie(title: movie.title ?? "",
                             score: voteAverage ?? "",
                             imageURL: movie.poster_path ?? "",
                             overview: movie.overview ?? "")
            }
        }
        
        return requestTopRatedMovies
            .asObservable()
            .map(convertMovieArrayFromResponse)
    }
}

struct ResMoviesData: Decodable {
    var page: Int?
    var results: [ResMovie]?
    var total_results: Int?
    var total_pages: Int?
}

struct ResMovie: Decodable {
    var poster_path: String?
    var adult: Bool?
    var overview: String?
    var release_date: String?
    var genre_ids: [Int]?
    var id: Int?
    var original_title: String?
    var original_language: String?
    var title: String?
    var backdrop_path: String?
    var popularity: Decimal?
    var vote_count: Int?
    var video: Bool?
    var vote_average: Decimal?
}
