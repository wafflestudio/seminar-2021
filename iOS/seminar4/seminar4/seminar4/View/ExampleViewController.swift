//
//  ExampleViewController.swift
//  seminar4
//
//  Created by 한상현 on 2021/11/07.
//

import UIKit
import RxSwift
import RxCocoa

class ExampleViewController: UIViewController {
    let vc = ChildViewController(parent: self)
    
    var disposeBag = DisposeBag()
    
    let viewModel = ViewModel()
    
    deinit {
//        self.disposeBag = nil
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.bindViewModel()
    }
    
    func bindViewModel() {
        self.viewModel.bindMovieData()
            .bind(to: configureCell)
    }
    
    
    func ifFetchNeeded {
        self.viewModel.fetchMovieDatas()
    }
}

class ViewModel {
    var data: [Movie] = []
    let subject = BehaviorSubject<[Movies]>(value: [])
    
    var disposeBag = DisposeBag()
    
    func fetchMovieDatas() {
        self.data += movies
    }
    
    func bindMovieData() -> Observable<[Movie]>{
        return Observable.create { obserber in
            APIPopularMovies.request(page: 1)
                .subscribe(onNext: { movies in
                    obserber.onNext(movies)
                }).disposed(by: self.disposeBag)
            
            Disposables.create()
        }
    }
}


class ChildViewController: UIViewController {
    
    let parent: UIViewController
    init(parent: UIViewController) {
        self.parent = parent
    }
}

// Auto Retain count (ARC)
