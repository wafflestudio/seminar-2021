//
//  MovieCollectionViewCell.swift
//  seminar4
//
//  Created by 한상현 on 2021/11/20.
//

import Kingfisher

class MovieCollectionViewCell: UICollectionViewCell {
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var scoreLabel: UILabel!
    @IBOutlet weak var posterImageView: UIImageView!
    
    func setData(title: String, score: String, posterURL: String){
        self.titleLabel.text = title
        self.scoreLabel.text = score
        self.contentView.backgroundColor = .gray
        
        let url = APIConstants.imageBaseURL + posterURL
        let processor = DownsamplingImageProcessor(size: posterImageView.bounds.size)
        
        KF.url(URL(string: url))
            .setProcessor(processor)
            .transition(.fade(0.5))
            .set(to: self.posterImageView)
    }
}

