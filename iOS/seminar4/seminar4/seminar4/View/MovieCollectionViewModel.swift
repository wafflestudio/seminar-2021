//
//  MovieCollectionViewModel.swift
//  seminar4
//
//  Created by 한상현 on 2021/11/20.
//

import RxSwift

class MovieCollectionViewModel {
    let movieDataSubject: BehaviorSubject<[Movie]> = BehaviorSubject(value: [])
    var currentPage: Int = 1
    var currentMovieItemCount: Int = 0
    
    private var disposeBag = DisposeBag()
    
    func fetchNextPageData(collectionViewType: MovieViewType) {
        var moviesOfNextPage: Observable<[Movie]>
        
        switch collectionViewType {
        case .popular:
            moviesOfNextPage = APIPopularMovies.request(page: self.currentPage)
        case .latest:
            moviesOfNextPage = APITopRatedMovies.request(page: self.currentPage)
        }
        
        moviesOfNextPage
            .subscribe(onNext: { [weak self] newDatas in
                guard let self = self,
                      let previousDatas = try? self.movieDataSubject.value() else {
                    return
                }
                                            
                self.currentMovieItemCount = previousDatas.count + newDatas.count
                self.currentPage += 1
                
                self.movieDataSubject.on(.next(previousDatas + newDatas))
            }).disposed(by: self.disposeBag)
    }
}
