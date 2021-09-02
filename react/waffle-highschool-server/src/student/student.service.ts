import { Injectable } from '@nestjs/common';
import { StudentEntity } from './student.entity';
import { Repository } from 'typeorm';
import { InjectRepository } from '@nestjs/typeorm';
import { WrrsException } from '../common/exceptions/wrrs-exception';
import { DuplicatedStudentException } from './student.exception';

@Injectable()
export class StudentService {
  constructor(
    @InjectRepository(StudentEntity)
    private studentRepository: Repository<StudentEntity>,
  ) {}

  findAll(): Promise<StudentEntity[]> {
    return this.studentRepository.find();
  }

  find(id: number): Promise<StudentEntity> {
    return this.studentRepository.findOne(id);
  }

  async create(name: string, grade: 1 | 2 | 3) {
    const student = {
      name,
      grade,
    };
    const foundStudent = await this.studentRepository.findOne({
      where: student,
    });
    if (foundStudent) throw new DuplicatedStudentException();

    return await this.studentRepository.save(student);
  }

  update(id: number, name: string, grade: 1 | 2 | 3) {
    const student = {
      name,
      grade,
    };
    return this.studentRepository.update(id, student);
  }

  delete(id: number) {
    return this.studentRepository.delete(id);
  }
}
