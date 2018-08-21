/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CaptainsLogAppTestModule } from '../../../test.module';
import { ELRUpdateComponent } from 'app/entities/elr/elr-update.component';
import { ELRService } from 'app/entities/elr/elr.service';
import { ELR } from 'app/shared/model/elr.model';

describe('Component Tests', () => {
    describe('ELR Management Update Component', () => {
        let comp: ELRUpdateComponent;
        let fixture: ComponentFixture<ELRUpdateComponent>;
        let service: ELRService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CaptainsLogAppTestModule],
                declarations: [ELRUpdateComponent]
            })
                .overrideTemplate(ELRUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ELRUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ELRService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ELR(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.eLR = entity;
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
                    const entity = new ELR();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.eLR = entity;
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
