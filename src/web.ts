import { WebPlugin } from '@capacitor/core';

import type { CapTorchPlugin } from './definitions';

export class CapTorchWeb extends WebPlugin implements CapTorchPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  async loadImage(): Promise<{imageStr: string}> {
    console.log('loadImage');
    let returnVal = '';
    const listener = (e: any) => {
      console.log(`imagePickResult ${JSON.stringify(e)}`);
      returnVal = e;
    };
    addEventListener('imagePickResult', listener);
    removeEventListener('imagePickResult', listener);
    return {imageStr: returnVal};
  }
}
