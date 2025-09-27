export function durationSecondeToStr(time: number | undefined) {
  if (time) {
    const minutes = Math.floor(time / 60)
      .toString()
      .padStart(2, '0');
    const seconds = (time % 60).toString().padStart(2, '0');
    return `${minutes}:${seconds}`;
  }
  return '';
}
