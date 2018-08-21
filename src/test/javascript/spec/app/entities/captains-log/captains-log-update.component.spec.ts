/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CaptainsLogAppTestModule } from '../../../test.module';
import { CaptainsLogUpdateComponent } from 'app/entities/captains-log/captains-log-update.component';
import { CaptainsLogService } from 'app/entities/captains-log/captains-log.service';
import { CaptainsLog } from 'app/shared/model/captains-log.model';

describe('Component Tests', () => {
    describe('CaptainsLog Management Update Component', () => {
        let comp: CaptainsLogUpdateComponent;
        let fixture: ComponentFixture<CaptainsLogUpdateComponent>;
        let service: CaptainsLogService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CaptainsLogAppTestModule],
                declarations: [CaptainsLogUpdateComponent]
            })
                .overrideTemplate(CaptainsLogUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CaptainsLogUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CaptainsLogService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CaptainsLog(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.captainsLog = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CaptainsLog();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.captainsLog = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
