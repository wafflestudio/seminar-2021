import { Injectable } from '@nestjs/common';
import { Student } from './student.dto';

const dummyStudents: Student[] = [
  {
    id: 1,
    name: '김김김',
    grade: 1,
  },
  {
    id: 2,
    name: '이이이',
    grade: 1,
  },
  {
    id: 3,
    name: '이이이',
    grade: 2,
  },
];

@Injectable()
export class StudentService {
  getStudents(): Student[] {
    return dummyStudents;
  }
}
