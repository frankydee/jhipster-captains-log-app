import { NgModule } from '@angular/core';

import { CaptainsLogAppSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [CaptainsLogAppSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [CaptainsLogAppSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class CaptainsLogAppSharedCommonModule {}
