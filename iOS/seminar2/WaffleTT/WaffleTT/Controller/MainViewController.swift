//
//  ViewController.swift
//  WaffleTT
//
//  Created by Jinsup Keum on 2021/09/11.
//

import UIKit

class MainViewController: UIViewController {
    
    var timetableList: WaffleTimetableList?
    
    var selectedTimetableList: [WaffleTimetable] = []
    
    var timetableListCount: Int {
        return timetableList?.timetableList.count ?? 0
    }
    
    @IBOutlet weak var timetableTableView: UITableView!
    
    @IBAction func save(_ sender: UIButton) {
        saveTimetable()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setTimetableTableView()
        selectedTimetableList = WaffleTimetableManager.getSavedTimetable()
        getTimetableList()
    }
    
    private func setTimetableTableView() {
        timetableTableView.delegate = self
        timetableTableView.dataSource = self
        
        let nib = UINib(nibName: "TimetableTableViewCell", bundle: nil)
        timetableTableView.register(nib, forCellReuseIdentifier: "timetableCell")
        
        timetableTableView.reloadData()
    }
    
    private func getTimetableList() {
        WaffleNetwork.request(with: WaffleUrl.TIMETABLE_DATA_URL) { (timetableList) in
            DispatchQueue.main.async {
                self.timetableList = timetableList
                self.timetableTableView.reloadData()
            }
        } failure: { (error) in
            switch error {
            case .serverError:
                print("서버에 문제가 생겼습니다")
            case .parsingError:
                print("데이터를 처리하는데 문제가 발생했습니다")
            case .authError:
                print("권한이 없습니다")
            }
        }
    }
    
    private func saveTimetable() {
        WaffleTimetableManager.saveToUserDefault(timetableList: selectedTimetableList)
        
        let alert = UIAlertController(title: "저장되었습니다", message: "", preferredStyle: .alert)
        let action = UIAlertAction(title: "확인", style: .default, handler: nil)
        alert.addAction(action)
        
        present(alert, animated: true)
    }
}

extension MainViewController: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return timetableListCount
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cellIdentifier = "timetableCell"
        
        let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath)
        
        if let timetableCell = cell as? TimetableTableViewCell, let timetable = timetableList?.timetableList[indexPath.row] {
            
            timetableCell.delegate = self
            timetableCell.setTimetable(timetable)
            
            if selectedTimetableList.contains(timetable) {
                timetableCell.setCheck()
            } else {
                timetableCell.setUnCheck()
            }
        }
        
        return cell
    }
}

// MARK: Timetable View Cell Delegate
extension MainViewController: TimetableTableViewCellDelegate {
    func checkTimetable(_ cell: TimetableTableViewCell, timetable: WaffleTimetable) {
        if (selectedTimetableList.contains(timetable)) {
            selectedTimetableList = selectedTimetableList.filter { $0 != timetable }
        } else {
            selectedTimetableList.append(timetable)
        }
        timetableTableView.reloadData()
    }
}
