//
//  HomeViewController.swift
//  seminar4
//
//  Created by 한상현 on 2021/10/06.
//

import UIKit
import RxSwift
import RxCocoa
import RxDataSources
import Kingfisher


class HomeViewController: UIViewController {
    @IBOutlet weak var segmentedControl: UISegmentedControl!
    
    private var disposeBag = DisposeBag()
    var collectionTabbarController: UITabBarController! = nil
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
        
        self.segmentedControl.rx.value
            .asDriver()
            .drive(onNext: { [weak self] value in
                guard let self = self else { return }
                
                self.collectionTabbarController.selectedIndex = value
            }).disposed(by: self.disposeBag)
    }

    private func setupView() {
        self.segmentedControl.setTitle("Popular", forSegmentAt: 0)
        self.segmentedControl.setTitle("Latest", forSegmentAt: 1)
        
        
        let vc = UITabBarController()
        self.collectionTabbarController = vc
        vc.setViewControllers([MovieCollectionViewController.instance(type: .popular), MovieCollectionViewController.instance(type: .latest)], animated: false)
        vc.tabBar.isHidden = true
        guard let contentView = vc.view else {
            return
        }

        self.addChild(vc)
        self.view.addSubview(contentView)
        vc.didMove(toParent: self)
        
        contentView.translatesAutoresizingMaskIntoConstraints = false
        
        NSLayoutConstraint.activate([
            contentView.topAnchor.constraint(equalTo: self.segmentedControl.bottomAnchor),
            contentView.bottomAnchor.constraint(equalTo: self.view.bottomAnchor),
            contentView.leadingAnchor.constraint(equalTo: self.view.leadingAnchor),
            contentView.trailingAnchor.constraint(equalTo: self.view.trailingAnchor)
        ])
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        self.navigationController?.isNavigationBarHidden = true
    }
}
