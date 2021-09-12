//
//  TimetableTableViewCell.swift
//  WaffleTT
//
//  Created by Jinsup Keum on 2021/09/11.
//

import UIKit

protocol TimetableTableViewCellDelegate: class {
    func checkTimetable(_ cell: TimetableTableViewCell, timetable: WaffleTimetable)
}

class TimetableTableViewCell: UITableViewCell {
    
    @IBOutlet weak var title: UILabel!
    @IBOutlet weak var instructor: UILabel!
    
    @IBOutlet weak var checkButton: UIButton!
    @IBAction func save(_ sender: UIButton) {
        guard let timetable = timetable else { return }
        delegate?.checkTimetable(self, timetable: timetable)
    }
    
    private var timetable: WaffleTimetable?
    
    weak var delegate: TimetableTableViewCellDelegate?
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        // Configure the view for the selected state
    }
    
    func setTimetable(_ timetable: WaffleTimetable) {
        self.timetable = timetable
        title.text = timetable.course_title
        instructor.text = timetable.instructor
    }
    
    func setCheck() {
        checkButton.tintColor = .orange
    }
    
    func setUnCheck() {
        checkButton.tintColor = .opaqueSeparator
    }
}
