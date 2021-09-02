import { Injectable } from '@nestjs/common';
import { StudentEntity } from './student.entity';
import { Repository } from 'typeorm';
import { InjectRepository } from '@nestjs/typeorm';
import {
  BadDataException,
  DuplicatedStudentException,
  IdNotFoundException,
  InvalidGradeException,
  InvalidNameException,
} from './student.exception';
import { CreateStudentRequestDto } from './student.dto';
import { isEmpty } from 'lodash';

@Injectable()
export class StudentService {
  constructor(
    @InjectRepository(StudentEntity)
    private studentRepository: Repository<StudentEntity>,
  ) {
    this.getGuardedStudent.bind(this);
  }

  getGuardedStudent(
    student: CreateStudentRequestDto,
  ): Omit<StudentEntity, 'id'> {
    const { grade, name, profile_img } = student;
    const guardedGrade = (() => {
      if (grade !== 1 && grade !== 2 && grade !== 3)
        throw new InvalidGradeException();
      return grade;
    })();

    const guardedName = (() => {
      if (!name.match(/^[가-힣]{2,3}$/)) throw new InvalidNameException();
      return name;
    })();

    const guardedProfileImg = (() => {
      if (typeof profile_img !== 'string' && profile_img != null)
        throw new BadDataException();
      return profile_img || null;
    })();

    return {
      name: guardedName,
      grade: guardedGrade,
      profile_img: guardedProfileImg,
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

  async patch(id: number, data: Partial<StudentEntity>) {
    if (data.id || data.name || data.grade) throw new BadDataException();

    if (isEmpty(data)) return { success: true as const };

    const updateResult = await this.studentRepository.update(id, data);
    if (updateResult.affected === 0) throw new IdNotFoundException();

    return { success: true as const };
  }

  async create(student) {
    const guardedStudent = this.getGuardedStudent(student);
    const foundStudent = await this.studentRepository.findOne({
      where: guardedStudent,
    });
    if (foundStudent) throw new DuplicatedStudentException();

    return await this.studentRepository.save(guardedStudent);
  }

  async delete(id: number) {
    const deletedResult = await this.studentRepository.delete(id);
    if (deletedResult.affected === 0) throw new IdNotFoundException();
    return { success: true as const };
  }
}
