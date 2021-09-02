import { Body, Controller, Delete, Get, Param, Post } from '@nestjs/common';
import { StudentService } from './student.service';
import { StudentEntity } from './student.entity';
import { DeleteResult } from 'typeorm';

@Controller('student')
export class StudentController {
  constructor(private readonly studentService: StudentService) {}

  @Get()
  async getStudents(): Promise<StudentEntity[]> {
    return this.studentService.findAll();
  }

  @Post()
  async createStudent(
    @Body()
    body,
  ) {
    return this.studentService.create(body.name, body.grade);
  }

  @Get(':id')
  async getStudent(@Param() params): Promise<StudentEntity> {
    return this.studentService.find(params.id);
  }

  @Delete(':id')
  async deleteStudent(@Param() params): Promise<DeleteResult> {
    return this.studentService.delete(params.id);
  }
}
