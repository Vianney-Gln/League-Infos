import { formatTimestampToDateStr } from '../date-utils';
import { durationSecondeToStr } from '../time-utils';

describe('timeUtils', () => {
  it('should return a duration to the form mm:ss', () => {
    // GIVEN
    const seconds = 2854;

    // WHEN
    const durationStr = durationSecondeToStr(seconds);

    // THEN
    expect(durationStr).toEqual('47:34');
  });

  it('should return a duration to the form 0m:0s', () => {
    // GIVEN
    const seconds = 185;

    // WHEN
    const durationStr = durationSecondeToStr(seconds);

    // THEN
    expect(durationStr).toEqual('03:05');
  });
});
