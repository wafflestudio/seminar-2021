import { ApiProperty } from '@nestjs/swagger';

export type validGrade = 1 | 2 | 3;
export type validName = string;

export class CreateStudentRequestDto {
  @ApiProperty({ description: '이름', example: '김와플', required: true })
  name: string;
  @ApiProperty({ description: '학년', example: 2, required: true })
  grade: number;
}

export class DeleteStudentResponseDto {
  @ApiProperty({ description: '성공 여부' })
  success: boolean;
}
