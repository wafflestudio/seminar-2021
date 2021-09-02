import { Injectable } from '@nestjs/common';
import { StudentEntity } from './student.entity';
import { Repository } from 'typeorm';
import { InjectRepository } from '@nestjs/typeorm';

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

  create(name: string, grade: 1 | 2 | 3) {
    const student = {
      name,
      grade,
    };
    return this.studentRepository.save(student);
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
