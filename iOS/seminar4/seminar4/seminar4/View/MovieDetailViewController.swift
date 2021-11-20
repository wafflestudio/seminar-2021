//
//  MovieDetailViewController.swift
//  seminar4
//
//  Created by 한상현 on 2021/10/18.
//

import UIKit
import Kingfisher

class MovieDetailViewController: UIViewController {

    var selectedMovie: Movie!
    
    @IBOutlet weak var posterImageView: UIImageView!
    @IBOutlet weak var movieTitleLabel: UILabel!
    @IBOutlet weak var movieRatingLabel: UILabel!
    @IBOutlet weak var movieOverviewTextView: UITextView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.setupView()
    }
    
    func setupView() {
        self.navigationController?.isNavigationBarHidden = false
        
        self.movieTitleLabel.text = self.selectedMovie.title
        self.movieRatingLabel.text = self.selectedMovie.score
        self.movieOverviewTextView.text = self.selectedMovie.overview
        
        let url = APIConstants.baseURL + self.selectedMovie.imageURL
        let processor = DownsamplingImageProcessor(size: self.posterImageView.bounds.size)
        
        KF.url(URL(string: url))
            .setProcessor(processor)
            .transition(.fade(0.5))
            .set(to: self.posterImageView)
    }
    
    static func instance(movie: Movie) -> UIViewController {
        let storyboard = UIStoryboard.init(name: "Main", bundle: nil)
        guard let vc = storyboard.instantiateViewController(identifier: "MovieDetail") as? Self else {
            return UIViewController()
        }
        
        vc.selectedMovie = movie
        return vc
    }
}
