import { Controller, Get } from '@nestjs/common';
import { StudentService } from './student.service';
import { Student } from './student.dto';

@Controller('student')
export class StudentController {
  constructor(private readonly studentService: StudentService) {}

  @Get()
  getStudents(): Student[] {
    return this.studentService.getStudents();
  }
}
