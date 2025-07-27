import { formatTimestampToDateStr } from '../date-utils';

describe('dateUtils', () => {
  it('should return a date to the form Le jj/mm/yyyy à 00h00 from a timestamp', () => {
    // GIVEN
    const timestamp = 2300561081488;

    // WHEN
    const dateStr = formatTimestampToDateStr(timestamp);

    // THEN
    expect(dateStr).toEqual('Le 25/11/2042 à 21h44');
  });
});
