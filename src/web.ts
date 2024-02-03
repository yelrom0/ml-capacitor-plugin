import { WebPlugin } from '@capacitor/core';

import type { CapTorchPlugin, ImageData } from './definitions';

export class CapTorchWeb extends WebPlugin implements CapTorchPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  async loadImage(): Promise<{image: ImageData}> {
    console.log('loadImage');
    let returnVal = {name: '', data: '', mimeType: ''} as ImageData;
    const listener = (e: any) => {
      console.log(`imagePickResult ${JSON.stringify(e)}`);
      returnVal = e;
    };
    addEventListener('imagePickResult', listener);
  
    return {image: returnVal};
  }
}
