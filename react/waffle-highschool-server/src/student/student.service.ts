import { Injectable } from '@nestjs/common';
import { StudentEntity } from './student.entity';
import { Repository } from 'typeorm';
import { InjectRepository } from '@nestjs/typeorm';
import {
  DuplicatedStudentException,
  IdNotFoundException,
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

  async find(id: number): Promise<StudentEntity> {
    const foundStudent = await this.studentRepository.findOne(id);
    if (!foundStudent) throw new IdNotFoundException();
    return foundStudent;
  }

  async create(name: string, grade: number) {
    const student = this.getGuardedStudent(name, grade);
    const foundStudent = await this.studentRepository.findOne({
      where: student,
    });
    if (foundStudent) throw new DuplicatedStudentException();

    return await this.studentRepository.save(student);
  }

  async delete(id: number) {
    const deletedResult = await this.studentRepository.delete(id);
    if (deletedResult.affected === 0) throw new IdNotFoundException();
    return { success: true };
  }
}
