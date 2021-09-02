import { Injectable } from '@nestjs/common';
import { StudentEntity } from './student.entity';
import { Repository } from 'typeorm';
import { InjectRepository } from '@nestjs/typeorm';
import {
  DuplicatedStudentException,
  InvalidGradeException,
  InvalidNameException,
} from './student.exception';
import { validGrade, validName } from './student.dto';

@Injectable()
export class StudentService {
  constructor(
    @InjectRepository(StudentEntity)
    private studentRepository: Repository<StudentEntity>,
  ) {
    this.getGuardedStudent.bind(this);
  }

  getGuardedStudent(
    name: string,
    grade: number,
  ): { name: validName; grade: validGrade } {
    const guardedGrade = (() => {
      if (grade !== 1 && grade !== 2 && grade !== 3)
        throw new InvalidGradeException();
      return grade;
    })();

    const guardedName = (() => {
      if (!name.match(/^[가-힣]{2,3}$/)) throw new InvalidNameException();
      return name;
    })();

    return {
      name: guardedName,
      grade: guardedGrade,
    };
  }

  findAll(): Promise<StudentEntity[]> {
    return this.studentRepository.find();
  }

  find(id: number): Promise<StudentEntity> {
    return this.studentRepository.findOne(id);
  }

  async create(name: string, grade: number) {
    const student = this.getGuardedStudent(name, grade);
    const foundStudent = await this.studentRepository.findOne({
      where: student,
    });
    if (foundStudent) throw new DuplicatedStudentException();

    return await this.studentRepository.save(student);
  }

  update(id: number, name: string, grade: number) {
    const student = this.getGuardedStudent(name, grade);
    return this.studentRepository.update(id, student);
  }

  delete(id: number) {
    return this.studentRepository.delete(id);
  }
}
