const isAppropriateName = (name) => {
  return /^[ㄱ-ㅎ|가-힣]{2,3}$/.test(name);
}

const isAppropriateGrade = (grade) => {
  console.log(grade);
  return /^0*[1-3]$/.test(grade)
}

export { isAppropriateName, isAppropriateGrade }