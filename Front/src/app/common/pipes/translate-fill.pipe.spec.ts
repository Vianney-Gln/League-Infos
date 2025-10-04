import { TranslateFillPipe } from './translate-fill.pipe';

describe('TranslateFillPipe', () => {
  it('create an instance', () => {
    const pipe = new TranslateFillPipe();
    expect(pipe).toBeTruthy();
  });

  it('should translate queue code into queue displayed for IHM', () => {
    // GIVEN
    const pipe = new TranslateFillPipe();

    // WHEN
    const flex = pipe.transform('RANKED_FLEX_SR');
    const soloQ = pipe.transform('RANKED_SOLO_5x5');

    // THEN
    expect(flex).toEqual('Classée flexible');
    expect(soloQ).toEqual('Classée solo');
  });
});
