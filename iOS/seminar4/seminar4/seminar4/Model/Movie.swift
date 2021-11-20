//
//  Movie.swift
//  seminar4
//
//  Created by 한상현 on 2021/10/18.
//

class Movie {
    let title: String
    let score: String
    let imageURL: String
    let overview: String
    
    init(title: String,
         score: String,
         imageURL: String,
         overview: String){
        self.title = title
        self.score = score
        self.imageURL = imageURL
        self.overview = overview
    }
}
