import { WebPlugin } from '@capacitor/core';

import type { CapTorchPlugin } from './definitions';

export class CapTorchWeb extends WebPlugin implements CapTorchPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
