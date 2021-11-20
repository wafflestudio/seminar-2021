//
//  MovieCollectionViewController.swift
//  seminar4
//
//  Created by 한상현 on 2021/11/02.
//

import UIKit
import RxSwift
import RxDataSources
import RxAppState

enum MovieViewType {
    case latest
    case popular
}

enum MovieSection {
    case movie
}

enum MovieItem {
    case movieCell(movie: Movie)
}

class MovieCollectionViewController: UIViewController {
    struct Constants {
        static let numberOfColumnsPerRow: CGFloat = 2
        static let lineBetweenCells: CGFloat = 10.0
    }
    
    typealias MovieModel = SectionModel<MovieSection, MovieItem>
    typealias MovieCollectionViewDataSource = RxCollectionViewSectionedReloadDataSource<MovieModel>
        
    @IBOutlet weak var collectionView: UICollectionView!
    
    var type: MovieViewType = .popular
    let viewModel = MovieCollectionViewModel()
    
    private var disposeBag = DisposeBag()
    
    private lazy var collectionViewDataSource = MovieCollectionViewDataSource(configureCell: self.colletionViewConfigureCell)
    private lazy var colletionViewConfigureCell: MovieCollectionViewDataSource.ConfigureCell = { [weak self] dataSource, collectionView, indexPath, item in
        guard let self = self else {
            return UICollectionViewCell()
        }
        
        switch item {
        case .movieCell(let movie):
            let cell: MovieCollectionViewCell = collectionView.dequeueReusableCell(withReuseIdentifier: "movieCollectionViewCell", for: indexPath) as! MovieCollectionViewCell
            cell.setData(title: movie.title, score: movie.score, posterURL: movie.imageURL)
            return cell
        }
    }
    
    override func viewDidLoad() {
        self.rx.viewDidLayoutSubviews
            .take(1)
            .asDriver(onErrorDriveWith: .never())
            .drive(onNext: { [weak self] in
                guard let self = self else { return }
                self.bindCollectionView()
            }).disposed(by: self.disposeBag)
    }
    
    private func bindCollectionView() {
        self.collectionView.prefetchDataSource = self
        
        self.collectionView.rx.setDelegate(self)
            .disposed(by: self.disposeBag)
        
        self.viewModel.movieDataSubject
            .map { movies in
                return [MovieModel(model: .movie, items: movies.map { .movieCell(movie: $0)})]
            }
            .bind(to: self.collectionView.rx.items(dataSource: self.collectionViewDataSource))
            .disposed(by: self.disposeBag)
        
        self.collectionView.rx.modelSelected(MovieItem.self)
            .asDriver()
            .drive(onNext: { [weak self] item in
                switch item {
                case .movieCell(let movie):
                    let vc = MovieDetailViewController.instance(movie: movie)
                    self?.navigationController?.pushViewController(vc, animated: true)
                }
            }).disposed(by: self.disposeBag)
        
        self.viewModel.fetchNextPageData(collectionViewType: self.type)
    }
    
    static func instance(type: MovieViewType) -> UIViewController {
        let storyboard = UIStoryboard.init(name: "Main", bundle: nil)
        guard let vc = storyboard.instantiateViewController(identifier: "MovieCollection") as? Self else {
            return UIViewController()
        }
        
        vc.type = type
        return vc
    }
}


extension MovieCollectionViewController: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let length = (self.view.frame.width - Constants.lineBetweenCells * (Constants.numberOfColumnsPerRow + 1)) / Constants.numberOfColumnsPerRow
        
        return CGSize(width: length, height: length)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {
        let inset = Constants.lineBetweenCells
        return UIEdgeInsets(top: inset, left: inset, bottom: inset, right: inset)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return Constants.lineBetweenCells
    }
}

extension MovieCollectionViewController: UICollectionViewDataSourcePrefetching {
    func collectionView(_ collectionView: UICollectionView, prefetchItemsAt indexPaths: [IndexPath]) {
        if indexPaths.contains(where: { indexPath in indexPath.row == self.viewModel.currentMovieItemCount - 1 }) {
            self.viewModel.fetchNextPageData(collectionViewType: self.type)
        }
    }
}
