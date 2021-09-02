import { Entity, Column, PrimaryGeneratedColumn } from 'typeorm';
import { validGrade, validName } from './student.dto';

@Entity()
export class StudentEntity {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  name: validName;

  @Column()
  grade: validGrade;
}
