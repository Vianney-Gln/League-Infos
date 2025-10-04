import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'translateFill',
  standalone: true,
})
export class TranslateFillPipe implements PipeTransform {
  transform(value: string | undefined): string {
    if (value === 'RANKED_FLEX_SR') {
      return 'Classée flexible';
    }

    if (value === 'RANKED_SOLO_5x5') {
      return 'Classée solo';
    }

    return '';
  }
}
