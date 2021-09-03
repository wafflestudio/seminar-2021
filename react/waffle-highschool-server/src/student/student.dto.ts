import { ApiProperty } from '@nestjs/swagger';

export type validGrade = 1 | 2 | 3;
export type validName = string;

export class CreateStudentRequestDto {
  @ApiProperty({ description: '이름', example: '김와플', required: true })
  name: string;
  @ApiProperty({ description: '학년', example: 2, required: true })
  grade: number;
  @ApiProperty({
    description: '프로필 사진 링크',
    example: 'https://wafflestudio.com',
    required: true,
  })
  profile_img: string | null;
}

export class PatchStudentRequestDto {
  @ApiProperty({ description: '수정 가능한 데이터', required: false })
  profile_img?: string;
}

export class PatchStudentResponseDto {
  @ApiProperty({ description: '성공 여부' })
  success: true;
}

export class DeleteStudentResponseDto {
  @ApiProperty({ description: '성공 여부' })
  success: true;
}
