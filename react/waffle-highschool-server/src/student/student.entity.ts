import { Entity, Column, PrimaryGeneratedColumn } from 'typeorm';
import { validGrade, validName } from './student.dto';
import { ApiProperty } from '@nestjs/swagger';

@Entity()
export class StudentEntity {
  @PrimaryGeneratedColumn()
  @ApiProperty({ description: 'id', example: 14112 })
  id: number;

  @Column()
  @ApiProperty({ description: '이름', example: '김와플' })
  name: validName;

  @Column()
  @ApiProperty({ description: '학년', example: 2 })
  grade: validGrade;
}
